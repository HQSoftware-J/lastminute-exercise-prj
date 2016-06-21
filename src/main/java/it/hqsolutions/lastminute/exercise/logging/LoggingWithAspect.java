package it.hqsolutions.lastminute.exercise.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ch.qos.logback.classic.Level;

@Configurable
@Component
@Aspect
public class LoggingWithAspect {
	private static Logger logger = LoggerFactory.getLogger("HQSLogProfiler");
	private static final int logLevel = ((ch.qos.logback.classic.Logger) logger).getEffectiveLevel().toInt();
	private static ObjectMapper mapper = null;

	@Value("${logging.dao.public.meth}")
	private String logDaoPubMeth;
	@Value("${logging.dao.public.meth.io}")
	private String logDaoPubMethIO;
	@Value("${logging.dao.protected.meth}")
	private String logDaoProtMeth;
	@Value("${logging.dao.protected.meth.io}")
	private String logDaoProtMethIO;
	@Value("${logging.dao.private.meth}")
	private String logDaoPrivMeth;
	@Value("${logging.dao.private.meth.io}")
	private String logDaoPrivMethIO;
	@Value("${logging.bl.public.meth}")
	private String logBlPubMeth;
	@Value("${logging.bl.public.meth.io}")
	private String logBlPubMethIO;
	@Value("${logging.bl.protected.meth}")
	private String logBlProtMeth;
	@Value("${logging.bl.protected.meth.io}")
	private String logBlProtMethIO;
	@Value("${logging.bl.private.meth}")
	private String logBlPrivMeth;
	@Value("${logging.bl.private.meth.io}")
	private String logBlPrivMethIO;

	@Value("${logging.systemout}")
	private String logSystemOut;

	private ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		}
		return mapper;
	}

	@Around("within(*..persistence.dao.implementation..*) && execution(public * * (..))")
	public Object perfAndIODAOPublicMeth(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkPerfAndIO(joinPoint, logDaoPubMeth, logDaoPubMethIO);
	}

	@Around("within(*..persistence.dao.implementation..*) && execution(protected * * (..))")
	public Object perfAndIODAOProtMeth(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkPerfAndIO(joinPoint, logDaoProtMeth, logDaoProtMethIO);
	}

	@Around("within(*..persistence.dao.implementation..*) && execution(private * * (..))")
	public Object perfAndIODAOPrivMeth(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkPerfAndIO(joinPoint, logDaoPrivMeth, logDaoPrivMethIO);
	}

	@Around("within(*..bl.bo.implementation..*) && execution(public * * (..))")
	public Object perfAndIOBusSvcPublicMeth(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkPerfAndIO(joinPoint, logBlPubMeth, logBlPubMethIO);
	}

	@Around("within(*..bl.bo.implementation..*) && execution(protected * * (..))")
	public Object perfAndIOBusSvcProtMeth(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkPerfAndIO(joinPoint, logBlProtMeth, logBlProtMethIO);
	}

	@Around("within(*..bl.bo.implementation..*) && execution(private * * (..))")
	public Object perfAndIOBusSvcPrivMeth(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkPerfAndIO(joinPoint, logBlPrivMeth, logBlPrivMethIO);
	}

	public Object checkPerfAndIO(ProceedingJoinPoint joinPoint, String logMethLevel, String logIOLevel)
			throws Throwable {
		boolean traceIO = false;
		// NOOP
		if (logLevel > Level.toLevel(logMethLevel).toInt()) {
			return joinPoint.proceed();
		}
		traceIO = logLevel <= Level.toLevel(logIOLevel).toInt();
		return perfAndIO(joinPoint, traceIO, logMethLevel);
	}

	public Object perfAndIO(ProceedingJoinPoint joinPoint, boolean traceIO, String logMethLevel) throws Throwable {
		String meth = retrieveFQNMethod(joinPoint);
		String income = traceIO ? retrieveInputArgs(joinPoint.getArgs()) : "(..)";
		String message = meth + income;
		logAtlevel(logMethLevel, message);

		long start = System.currentTimeMillis();
		Object outcomeObj = joinPoint.proceed();
		long elapsedTime = System.currentTimeMillis() - start;

		String outcome = "(N/A)";
		try {
			outcome = traceIO ? outcomeObj == null ? "" : getMapper().writeValueAsString(outcomeObj) : "(..)";
			message = meth + ":" + outcome + "(" + elapsedTime + "ms)";
		} catch (Throwable t) {
			if (logger.isErrorEnabled()) {
				logger.error("Error logging: ", t.getMessage());
			}
		}
		logAtlevel(logMethLevel, message);
		return outcomeObj;
	}

	private void logAtlevel(String logMethLevel, String message) {
		switch (Level.toLevel(logMethLevel).toInt()) {
		case Level.TRACE_INT:
			logger.trace(message);
			break;
		case Level.DEBUG_INT:
			logger.debug(message);
			break;
		case Level.INFO_INT:
			logger.info(message);
			break;
		// Sounds weird if you configure a level higher than INFO but...
		case Level.WARN_INT:
			logger.warn(message);
			break;
		case Level.ERROR_INT:
			logger.error(message);
			break;
		}
		if (logLevel <= Level.toLevel(logSystemOut).toInt()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
			String date = sdf.format(new Date());
			long threadId = Thread.currentThread().getId();
			System.out.println(message);
		}
	}

	private String retrieveInputArgs(Object[] args) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			try {
				String argToWrite = getMapper().writeValueAsString(arg);
				buffer.append(argToWrite);
			} catch (JsonProcessingException e) {
				StackTraceElement[] elements = e.getStackTrace();
				logger.error("AspectLoggingException:" + elements[0] + "\n" + elements[1]);
			}
			if (i != args.length - 1) {
				buffer.append(",");
			}
		}
		buffer.append(")");
		return buffer.toString();
	}

	private String retrieveFQNMethod(ProceedingJoinPoint joinPoint) {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String method = joinPoint.getSignature().getName();
		return className + "." + method;
	}
}

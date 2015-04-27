package pl.edu.agh.integr10s.lifepl.model.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Function that calculates same values for one argument
 *
 * @param <T> Type of parameter, should implement hashcode and equals methods of object.
 * @param <R> Type of result
 */
public abstract class IdempotentFunction<T, R> {

    private static Logger logger = LoggerFactory.getLogger(IdempotentFunction.class);
    private final Type argumentType;
    private final Type resultType;
    private Map<T, R> storedResults = new HashMap<>();

    protected IdempotentFunction() {
        Type[] genericTypes = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        argumentType = genericTypes[0];
        resultType = genericTypes[1];
    }

    public Type getArgumentType() {
        return argumentType;
    }

    public Type getResultType() {
        return resultType;
    }

    public R calculateFor(T argument) {
        checkNotNull(argument, "argument can not be null");
        logger.debug("calculate function result of type '{}' for argument of type '{}'", resultType, argumentType);

        R storedResult = storedResults.get(argument);
        if (storedResult == null) {
            storedResult = apply(argument);
            storedResults.put(argument, storedResult);
            logger.debug("function is called first time for argument '{}', calculating result '{}'", argument, storedResult);
        } else {
            logger.debug("using cached result for this argument : '{}'", storedResult);
        }

        return storedResult;
    }

    protected abstract R apply(T argument);

    protected Optional<R> getStoredResultForArgument(T argument) {
        checkNotNull(argument);
        return Optional.ofNullable(storedResults.get(argument));
    }
}

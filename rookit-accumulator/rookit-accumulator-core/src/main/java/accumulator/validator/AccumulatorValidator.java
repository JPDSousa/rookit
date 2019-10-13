package accumulator.validator;

import accumulator.Accumulator;
import org.rookit.utils.log.AbstractLogCategory;
import org.rookit.utils.log.LogManager;
import org.rookit.utils.log.validator.AbstractValidator;

@SuppressWarnings({"ClassWithoutLogger", "StaticVariableOfConcreteClass"})
public final class AccumulatorValidator extends AbstractValidator {

    private static final Validator ourInstance = new AccumulatorValidator();
    private static final String NAME = "Rookit-Accumulator";

    public static Validator getInstance() {
        return ourInstance;
    }

    private AccumulatorValidator() {
        super(LogManager.create(new AccumulatorLogCategory()));
    }

    private static class AccumulatorLogCategory extends AbstractLogCategory {
        @Override
        public final String getName() {
            return NAME;
        }

        @Override
        public final Package getPackage() {
            return Accumulator.class.getPackage();
        }
    }
}

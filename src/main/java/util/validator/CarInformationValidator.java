package util.validator;

public class CarInformationValidator {

    private static final CarInformationValidator INSTANCE = new CarInformationValidator();

    public static CarInformationValidator getInstance() {
        return INSTANCE;
    }

    private CarInformationValidator() {
    }

    private static final String NAME_FORMAT_REGEX = ".{2,30}";

    public boolean validate(String mark,
                            String model,
                            String color) {
        return
                mark.matches(NAME_FORMAT_REGEX) &&
                        model.matches(NAME_FORMAT_REGEX) &&
                        color.matches(NAME_FORMAT_REGEX);

    }
}
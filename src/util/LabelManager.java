package util;

public class LabelManager {

    private static int funLabCount=0;
    private static int label_count = 0;


    public static String freshLabel() {

        return "Function"+(funLabCount);
    }

    public static String endFreshLabel() {
        String endLabel = "endFunction"+funLabCount;
        funLabCount = funLabCount + 1;
        return endLabel;
    }

    public static String freshLabelglobal(String label_name) {
        label_count += 1;
        return "LABEL" + label_name + label_count;
    }

}

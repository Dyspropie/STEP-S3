package week5;

/**
 * Problem 2: Reference Desk Subclass Reach
 * Handles cross-package subclass visibility rules and string title casing.
 */
public class Problem2_ReferenceDeskSubclassReach {

    public static class AccessChecker {
        public static String classifyAccess(String fieldModifier, String accessorContext) {
            if (fieldModifier == null || accessorContext == null) return "DENIED";

            switch (fieldModifier.toLowerCase()) {
                case "public":
                    return "ALLOWED";
                case "private":
                    return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
                case "default":
                    return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext))
                            ? "ALLOWED" : "DENIED";
                case "protected":
                    if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                        return "ALLOWED";
                    }
                    if ("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) {
                        return "ALLOWED";
                    }
                    // Java JLS §6.6.2: protected member access across packages is DENIED through parent type reference
                    if ("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE".equals(accessorContext) ||
                        "DIFFERENT_PACKAGE".equals(accessorContext)) {
                        return "DENIED";
                    }
                    return "DENIED";
                default:
                    return "DENIED";
            }
        }

        public static String describeContext(String accessorContext) {
            if (accessorContext == null || accessorContext.isEmpty()) return "";

            String[] words = accessorContext.split("_");
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < words.length; i++) {
                String word = words[i].toLowerCase();
                if (!word.isEmpty()) {
                    sb.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1));
                }
                if (i < words.length - 1) {
                    sb.append(" ");
                }
            }

            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(AccessChecker.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(AccessChecker.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(AccessChecker.describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}

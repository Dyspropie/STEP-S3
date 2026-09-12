package week5;

/**
 * Problem 1: Membership Field Reach Checker
 * Evaluates real Java access visibility rules and validates constructor string length.
 */
public class Problem1_MembershipFieldReachChecker {

    public static class AccessChecker {
        public static String classifyAccess(String fieldModifier, String accessorContext) {
            if (fieldModifier == null || accessorContext == null) {
                return "DENIED";
            }

            switch (fieldModifier.toLowerCase()) {
                case "public":
                    return "ALLOWED";
                case "protected":
                    if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                        return "ALLOWED";
                    }
                    return "DENIED";
                case "default":
                    if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                        return "ALLOWED";
                    }
                    return "DENIED";
                case "private":
                    if ("SAME_CLASS".equals(accessorContext)) {
                        return "ALLOWED";
                    }
                    return "DENIED";
                default:
                    return "DENIED";
            }
        }

        public static String summarizeByModifier(String[][] attempts) {
            int privAllowed = 0, privDenied = 0;
            int defAllowed = 0, defDenied = 0;
            int protAllowed = 0, protDenied = 0;
            int pubAllowed = 0, pubDenied = 0;

            if (attempts != null) {
                for (String[] attempt : attempts) {
                    if (attempt == null || attempt.length < 2) continue;
                    String mod = attempt[0];
                    String ctx = attempt[1];
                    String res = classifyAccess(mod, ctx);
                    boolean isAllowed = "ALLOWED".equals(res);

                    switch (mod.toLowerCase()) {
                        case "private":
                            if (isAllowed) privAllowed++; else privDenied++;
                            break;
                        case "default":
                            if (isAllowed) defAllowed++; else defDenied++;
                            break;
                        case "protected":
                            if (isAllowed) protAllowed++; else protDenied++;
                            break;
                        case "public":
                            if (isAllowed) pubAllowed++; else pubDenied++;
                            break;
                    }
                }
            }

            return String.format("private: %d allowed / %d denied | default: %d allowed / %d denied | protected: %d allowed / %d denied | public: %d allowed / %d denied",
                    privAllowed, privDenied, defAllowed, defDenied, protAllowed, protDenied, pubAllowed, pubDenied);
        }
    }

    public static class LibraryMember {
        private String membershipId;
        String branchCode;
        protected double finesOwed;
        public String displayName;

        // No usable no-argument constructor
        private LibraryMember() {}

        public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            if (membershipId == null || membershipId.trim().length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.membershipId = membershipId.trim();
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }
    }

    public static void main(String[] args) {
        System.out.println(AccessChecker.classifyAccess("private", "SAME_CLASS"));
        System.out.println(AccessChecker.classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(AccessChecker.summarizeByModifier(attempts));

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (Exception e) {
            System.out.println("new LibraryMember(\"LB9\", ...) -> " + e.getMessage());
        }
    }
}

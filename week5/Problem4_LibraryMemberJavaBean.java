package week5;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Problem 4: LibraryMember JavaBean, Chained Constructors & Security Answer
 * Follows JavaBean naming conventions, constructor chaining, write-once ID, and write-only security answer.
 */
public class Problem4_LibraryMemberJavaBean {

    public static class LibraryMember {
        private String membershipId;
        private boolean membershipIdInitialized = false;
        private String name;
        private boolean premiumMember;
        private String securityAnswerHash;

        public LibraryMember() {
            this(null, null);
        }

        public LibraryMember(String name) {
            this(null, name);
        }

        public LibraryMember(String membershipId, String name) {
            if (membershipId != null) {
                this.membershipId = membershipId;
                this.membershipIdInitialized = true;
            }
            this.name = name;
        }

        public String getMembershipId() {
            return membershipId;
        }

        // Write-once setter: only takes effect on the first call
        public void setMembershipId(String id) {
            if (!membershipIdInitialized) {
                this.membershipId = id;
                this.membershipIdInitialized = true;
            }
            // Subsequent calls are silently ignored
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        // True write-only property: deterministic one-way hash, no getter exists
        public void setSecurityAnswer(String answer) {
            if (answer == null) {
                this.securityAnswerHash = null;
                return;
            }
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(answer.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    hexString.append(String.format("%02x", b));
                }
                this.securityAnswerHash = hexString.toString();
            } catch (Exception e) {
                this.securityAnswerHash = String.valueOf(answer.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("new LibraryMember(\"Priya Nair\").getMembershipId() = " +
                new LibraryMember("Priya Nair").getMembershipId());

        System.out.println("new LibraryMember(\"LIB-8841\", \"Priya Nair\").getMembershipId() = " +
                new LibraryMember("LIB-8841", "Priya Nair").getMembershipId());

        LibraryMember m = new LibraryMember();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000"); // Silently ignored
        System.out.println("After two setMembershipId calls: " + m.getMembershipId());
    }
}

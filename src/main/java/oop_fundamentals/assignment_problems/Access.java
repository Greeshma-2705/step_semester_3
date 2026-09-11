class ReferenceDesk {
protected String deskCode = "REF01";

protected void showDeskCode() {
    System.out.println("Desk Code: " + deskCode);
}

}

class ReferenceDeskSubclass extends ReferenceDesk {

void accessUsingOwnType() {

    // Allowed because subclass accesses
    // protected member using its own type.
    System.out.println("Own type access: " + deskCode);
}

void accessUsingParentType(ReferenceDesk desk) {

    // Parent-typed reference is not treated as
    // valid protected access across a different package.
    System.out.println(
            "Parent type reference: access restricted");
}

}

class AccessChecker2 {

static String classifyAccess(
        String modifier,
        String context) {

    if (modifier.equals("private")) {

        if (context.equals("SAME_CLASS")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    if (modifier.equals("default")) {

        if (context.equals("SAME_PACKAGE")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    if (modifier.equals("protected")) {

        if (context.equals("SAME_CLASS")
                || context.equals("SAME_PACKAGE")
                || context.equals("SUBCLASS_OWN_TYPE")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    if (modifier.equals("public")) {
        return "ALLOWED";
    }

    return "DENIED";
}

}

public class Access {

public static void main(String[] args) {

    System.out.println(
            "Protected access examples:");

    System.out.println(
            "Same class: "
                    + AccessChecker2.classifyAccess(
                    "protected",
                    "SAME_CLASS"));

    System.out.println(
            "Same package: "
                    + AccessChecker2.classifyAccess(
                    "protected",
                    "SAME_PACKAGE"));

    System.out.println(
            "Subclass own type: "
                    + AccessChecker2.classifyAccess(
                    "protected",
                    "SUBCLASS_OWN_TYPE"));

    System.out.println(
            "Different package: "
                    + AccessChecker2.classifyAccess(
                    "protected",
                    "DIFFERENT_PACKAGE"));

    ReferenceDeskSubclass desk =
            new ReferenceDeskSubclass();

    desk.accessUsingOwnType();
    desk.accessUsingParentType(
            new ReferenceDesk());
}
}

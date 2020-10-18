package ch.patchcode.jback.coreEntities;

public enum Authority implements ch.patchcode.jback.secModel.Privilege {

    /**
     * Authorized to create the {@link Person} that represents the own identity.
     */
    CAN_CREATE_OWN_PERSON {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanCreateOwnPerson();
        }
    },

    /**
     * Authorized to create some other {@link Person} that is not oneself.
     */
    CAN_CREATE_CLIENT_PERSON {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanCreateClientPerson();
        }
    },

    /**
     * Authorized to create {@link Club}s.
     */
    CAN_CREATE_CLUB {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanCreateClub();
        }
    },

    /**
     * Authorized to assign a member to a {@link Club}
     */
    CAN_ASSIGN_MEMBER {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanAssignMember();
        }
    },

    /**
     * Authorized to assign an admin to a {@link Club}
     */
    CAN_ASSIGN_ADMIN {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanAssignAdmin();
        }
    };

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R caseCanCreateOwnPerson();

        R caseCanCreateClientPerson();

        R caseCanCreateClub();

        R caseCanAssignMember();

        R caseCanAssignAdmin();
    }
}

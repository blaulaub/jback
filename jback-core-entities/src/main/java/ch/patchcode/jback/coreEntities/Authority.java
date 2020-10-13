package ch.patchcode.jback.coreEntities;

public enum Authority implements ch.patchcode.jback.secModel.Privilege {

    /**
     * Authorized to create the person that represents the own identity.
     */

    CAN_CREATE_OWN_PERSON {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanCreateOwnPerson();
        }
    },

    /**
     * Authorized to create some other person that is not oneself.
     */
    CAN_CREATE_CLIENT_PERSON {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanCreateClientPerson();
        }
    };

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R caseCanCreateOwnPerson();

        R caseCanCreateClientPerson();
    }
}

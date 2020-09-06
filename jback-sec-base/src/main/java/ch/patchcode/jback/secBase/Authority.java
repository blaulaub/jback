package ch.patchcode.jback.secBase;

public enum Authority {

    CAN_CREATE_PERSON {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.caseCanCreatePerson();
        }
    };

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R caseCanCreatePerson();
    }
}

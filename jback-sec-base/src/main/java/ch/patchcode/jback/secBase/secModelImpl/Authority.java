package ch.patchcode.jback.secBase.secModelImpl;

public enum Authority implements ch.patchcode.jback.secModel.Privilege {

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

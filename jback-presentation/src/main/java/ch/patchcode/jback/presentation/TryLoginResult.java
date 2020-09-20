package ch.patchcode.jback.presentation;

public enum TryLoginResult {

    SUCCESS {
        @Override
        public <R> R accept(VisitingProducer<R> visitingProducer) {

            return visitingProducer.caseSuccess();
        }
    },

    NEED_CONFIRMATION_CODE {
        @Override
        public <R> R accept(VisitingProducer<R> visitingProducer) {

            return visitingProducer.caseNeedConfirmationCode();
        }
    },

    UNKNOWN_USER {
        @Override
        public <R> R accept(VisitingProducer<R> visitingProducer) {

            return visitingProducer.caseUnknownUser();
        }
    };

    public abstract <R> R accept(VisitingProducer<R> visitingProducer);

    public interface VisitingProducer<R> {

        R caseSuccess();

        R caseNeedConfirmationCode();

        R caseUnknownUser();
    }

}

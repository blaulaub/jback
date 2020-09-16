package ch.patchcode.jback.util;

public enum Flag {

    NO {
        @Override
        public <R> R accept(VisitingProducer<R> visitingProducer) {
            return visitingProducer.caseNo();
        }

        @Override
        public void accept(VisitingAction visitingAction) {
            visitingAction.caseNo();
        }
    },

    YES {
        @Override
        public <R> R accept(VisitingProducer<R> visitingProducer) {
            return visitingProducer.caseYes();
        }

        @Override
        public void accept(VisitingAction visitingAction) {
            visitingAction.caseYes();
        }
    };

    public abstract <R> R accept(VisitingProducer<R> visitingProducer);

    public abstract void accept(VisitingAction visitingAction);

    public interface VisitingProducer<R> {

        R caseNo();

        R caseYes();
    }

    public interface VisitingAction {

        void caseNo();

        void caseYes();
    }
}

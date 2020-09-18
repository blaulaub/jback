package ch.patchcode.jback.secBase;

public interface VerificationMean {

    interface VerificationByConsole extends VerificationMean {
    }

    interface VerificationByEmail extends VerificationMean {
    }

    interface VerificationBySms extends VerificationMean {
    }
}

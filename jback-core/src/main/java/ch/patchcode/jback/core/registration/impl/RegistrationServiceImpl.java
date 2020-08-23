package ch.patchcode.jback.core.registration.impl;

import ch.patchcode.jback.core.registration.InitialRegistrationData;
import ch.patchcode.jback.core.registration.RegistrationService;
import ch.patchcode.jback.core.registration.VerificationMean;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void process(InitialRegistrationData data) {

        var verificationMeanVisitor = new MyVerificationMeanVisitor(data);
        verificationMeanVisitor.visit();
    }

    private class MyVerificationMeanVisitor implements VerificationMean.VerificationMeanVisitor {

        private final InitialRegistrationData data;

        public MyVerificationMeanVisitor(InitialRegistrationData data) {
            this.data = data;
        }

        public void visit() {
            data.accept(this);
        }

        @Override
        public void visit(VerificationMean.VerificationByConsole verificationByConsole) {
            // - send the user some key to trigger the confirmation step
            // - store the pending request (with timeout)
            throw new RuntimeException("not implemented");
        }

        @Override
        public void visit(VerificationMean.VerificationByEmail verificationByEmail) {
            // - send the user some key to trigger the confirmation step
            // - store the pending request (with timeout)
            throw new RuntimeException("not implemented");
        }

        @Override
        public void visit(VerificationMean.VerificationBySms verificationBySms) {
            // - send the user some key to trigger the confirmation step
            // - store the pending request (with timeout)
            throw new RuntimeException("not implemented");
        }
    }
}

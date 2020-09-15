import { VerificationMean } from './verification-mean';

export class InitialRegistrationData {

  firstName = null;

  lastName = null;

  verificationMean: VerificationMean = null;

  private notNullOrEmpty(str: string): boolean {
    return typeof str === "string" && str !== "";
  }

  isValid(): boolean {
    return this.notNullOrEmpty(this.firstName) &&
      this.notNullOrEmpty(this.lastName) &&
      this.verificationMean instanceof VerificationMean &&
      this.verificationMean.isValid();
  }
}

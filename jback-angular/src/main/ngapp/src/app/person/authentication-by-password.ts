import { AuthenticationMean } from './authentication-mean'

export class AuthenticationByPassword extends AuthenticationMean {

    readonly type = "password";

    username = "";

    password = "";

    isValid(): boolean {
        return typeof this.username === "string" && this.username !== "" &&
        typeof this.password === "string" && this.password !== "";
    }
}
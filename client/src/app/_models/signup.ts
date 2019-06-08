export class SignUp {
    username: string;
    email: string;
    password: string;
    role: string;
    name: string;

    constructor(u, e, p, r, n) {
        this.username = u;
        this.email = e;
        this.password = p;
        this.role = r;
        this.name = n;
    }
}
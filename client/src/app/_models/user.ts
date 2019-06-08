export class User {
    id: number;
    username: string;
    email: string;
    password: string;
    profileURL: string;
    expire: Date;
    name: string;
    role: string;
    token?: string;
}
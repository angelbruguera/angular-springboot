import { Question } from './question';
import { Answer } from './answer';

export class Practice {
    id: number;
    questions: Question[];
    preguntes: number;
    respostesContestades: number;
    respostesCorrectes: number;
    porcentatge: number;
    candidateUsername: string;
    skillImage: string;
    answers?: Answer[];
}
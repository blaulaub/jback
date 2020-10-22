import { Perspective } from './perspective';

export interface SessionInfo {

    isAuthenticated: boolean;

    principalName: string;

    perspective: keyof typeof Perspective;

    firstName: string | null;
  
    lastName: string | null;

    userId: string | null;

    personId: string | null;
}
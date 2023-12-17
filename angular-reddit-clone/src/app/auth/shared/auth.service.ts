import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SignupRequestPayload } from '../signup/signup-request.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient : HttpClient) { }
  baseUrl = "http://localhost:8080/api/auth/";

  signup(signupRequestPayload:SignupRequestPayload) : Observable<any>{
    return this.httpClient.post<Object>(`${this.baseUrl}/signup`,signupRequestPayload);
  }

}

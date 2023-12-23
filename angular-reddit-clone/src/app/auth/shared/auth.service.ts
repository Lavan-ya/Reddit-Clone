import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SignupRequestPayload } from '../signup/signup-request.payload';
import {Login} from '../login/login-payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient : HttpClient) { }
  baseUrl = "http://localhost:8080/api/auth";

  signup(signupRequestPayload:SignupRequestPayload) : Observable<String>{
    return this.httpClient.post<String>(`${this.baseUrl}/signup`,signupRequestPayload);
  }

  loggedIn(loginCred : Login):Observable<String>{
return this.httpClient.post<String>(`${this.baseUrl}/login`,loginCred);
  }

}

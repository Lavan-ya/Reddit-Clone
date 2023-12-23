import { Component } from '@angular/core';
import { FormControl, FormGroup,Validators } from '@angular/forms';
import { AuthService } from '../shared/auth.service';
import { Login } from './login-payload';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginCred:Login;

  loginFormGroup:FormGroup=new FormGroup({
    email: new FormControl('',[Validators.email,Validators.required]),
    password: new FormControl('',[Validators.required,Validators.maxLength(8),Validators.minLength(4)])
  })
constructor(private authService: AuthService){
this.loginCred={
  email:'',
  password:''
}
}
  login(){
    this.loginCred.email=this.loginFormGroup.get('email')?.value;
    this.loginCred.password=this.loginFormGroup.get('password')?.value;

    this.authService.loggedIn(this.loginCred).subscribe(data=>{
      console.log(data);
    })
  }

}

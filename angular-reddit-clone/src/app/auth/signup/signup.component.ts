import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { SignupRequestPayload } from './signup-request.payload';
import{AuthService} from '../shared/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{
  
  signupRequestPayload : SignupRequestPayload;

  signupForm: FormGroup = new FormGroup({
    name: new FormControl('',Validators.required),
    password: new FormControl('',[Validators.required,Validators.maxLength(8),Validators.minLength(4)]),
    email: new FormControl('',[Validators.email,Validators.required])
  })

  constructor(private authService : AuthService){
  this.signupRequestPayload={
    username:'',
    password:'',
    email:''
  }
}

signup(){
  this.signupRequestPayload.username = this.signupForm.get('name')?.value;
  this.signupRequestPayload.password=this.signupForm.get('password')?.value;
  this.signupRequestPayload.email=this.signupForm.get('email')?.value;
  this.authService.signup(this.signupRequestPayload).subscribe(data=>{
    alert(data);
  })
}

ngOnInit(): void {
 
}
}


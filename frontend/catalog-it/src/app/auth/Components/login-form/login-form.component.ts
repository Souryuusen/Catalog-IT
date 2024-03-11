import { RestService } from './../../../service/rest.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserLoginDTO } from '../../../entities/user';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  providers: [RestService],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {

  loginForm = new FormGroup({
    username: new FormControl("login", [Validators.minLength(4), Validators.maxLength(32), Validators.nullValidator, Validators.required]),
    password: new FormControl("password", [Validators.minLength(6), Validators.nullValidator, Validators.maxLength(64), Validators.required])
  });

  constructor(private restService: RestService, private router: Router) {

  }

  protected onSubmit() {
    let user: UserLoginDTO = {
      username: this.loginForm.value.username!,
      password: this.loginForm.value.password!
    }
    this.restService.authenticateUser(user).subscribe((loggedUser) => {
      sessionStorage.setItem('user', JSON.stringify(loggedUser));
      this.router.navigateByUrl("/");
    })
  }

}

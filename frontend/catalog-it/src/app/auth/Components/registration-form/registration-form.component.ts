import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RestService } from '../../../service/rest.service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-registration-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  providers: [RestService],
  templateUrl: './registration-form.component.html',
  styleUrl: './registration-form.component.css'
})

export class RegistrationFormComponent {

  registerForm = new FormGroup({
    username: new FormControl("username"),
    password: new FormControl("password"),
    email: new FormControl("email")
  });

  constructor(private restService: RestService, private router: Router) {

  }

  protected onSubmit() {
    console.log('click')
    // if(this.registerForm.) {
      let user = {
        username: this.registerForm.value.username!,
        password: this.registerForm.value.password!,
        email: this.registerForm.value.email!
      }
      console.log(user)
      this.restService.registerUser(user).subscribe((user1) => {
        console.log(user1)
        this.router.navigateByUrl("/login")
      })
    // }
  }

}

export interface UserRegisterDTO {
  username: string
  password: string
  email: string
}

export interface UserLoginDTO {
  username: string
  password: string
}

export interface UserDTO {
  userId: number
  username: string
  email: string
}

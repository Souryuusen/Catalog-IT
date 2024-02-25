import { Writer } from "./writer"
import { Director } from "./director"
import { Actor } from "./actor"
import { Tag } from "./tag"
import { Genre } from "./genre"

export interface Movie {
  movieId: number,
  title: string,
  originalTitle: string,
  runtime: string,
  countryOfOrigin: string,
  language: string,
  releaseDate: string,
  movieIdentifier: string,
  directors: Director[],
  writers: Writer[],
  genres: Genre[],
  keywords: Tag[],
  stars: Actor[],
  covers: string[]
}

export interface MovieShort {
  movieId: number,
  title: string,
  originalTitle: string,
  runtime: string,
  countryOfOrigin: string,
  language: string,
  releaseDate: string,
  cover: string
}

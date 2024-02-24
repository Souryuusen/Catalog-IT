import { Writer } from "./writer"
import { Director } from "./director"
import { Actor } from "./actor"
import { Tag } from "./tag"
import { Genre } from "./genre"

export interface Movie {
  id: number,
  title: string,
  originalTitle: string,
  runtime: string,
  releaseDate: string,
  countryOfOrigin: string,
  language: string,
  writers: Writer[],
  directors: Director[],
  actors: Actor[],
  tags: Tag[],
  genres: Genre[],
  coverUrl: string
}

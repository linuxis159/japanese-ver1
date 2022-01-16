package com.study.japanese.exception;

public class EntityNotFoundExcepiton extends NullPointerException{
        public EntityNotFoundExcepiton(){
            super();
        }
        public EntityNotFoundExcepiton(String message){

            super(message);
        }
}

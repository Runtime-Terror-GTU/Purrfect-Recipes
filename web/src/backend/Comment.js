class Comment{
    constructor(){
        this.CommentID="";
        this.CommentOwner="";
        this.Comment="";
    }
    //Return Comment Content
    getComment(){
        return this.Comment;
    }
    //Return Comment Owner
    getCOmmentOwner(){
        return this.CommentOwner;
    }
    // Return Comment ID                                                        
    getCommentID(){
        return this.CommentID;
    }
}
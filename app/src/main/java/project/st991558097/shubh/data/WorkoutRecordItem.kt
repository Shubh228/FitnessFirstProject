package project.st991558097.shubh.data


/*
Creator - Shubh Patel(991558097)
WorkoutRecordItem data class will help us to record the workout activity and will create snapshot for us to store into our database
 */
data class WorkoutRecordItem (
    var id: String ="",
    var name: String = "",
    var date: String = "",
    var startTime:String = "",
    var endTime: String ="",
    var duration: String ="",
    var rating: String = "",
    var feedback:String = "",
    var img:String = ""
)
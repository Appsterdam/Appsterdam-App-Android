package rs.appsterdam.app.domain.repository

import rs.appsterdam.app.data.model.Category
import rs.appsterdam.app.data.model.Event
import rs.appsterdam.app.data.model.Job
import rs.appsterdam.app.data.model.Member
import rs.appsterdam.app.data.model.Team

val CATEGORIES: List<Category> = listOf(
    Category(
        name = "Name",
        events = listOf(
            Event(
                id = "1",
                organizer = "Organizer",
                date = "Date",
                name = "Name",
                description = "Description",
                price = "Price",
                locationName = "Location Name",
                locationAddress = "Location Address",
                icon = "Icon",
                attendees = "Attendees",
                latitude = "Latitude",
                longitude = "Longitude"
            )
        )
    )
)

val JOBS: List<Job> = listOf(
    Job(
        jobUrl = "URL",
        jobTitle = "Title",
        jobDescription = "Description",
        jobShortDescription = "Short Description",
        jobPublishEndDate = "Publish End Date",
        jobID = "ID",
        jobProvider = "Provider",
        jobCity = "City"
    )
)

val TEAMS: List<Team> = listOf(
    Team(
        team = "Team",
        members = listOf(
            Member(
                name = "Name",
                picture = "Picture",
                function = "Function",
                twitter = "Twitter",
                linkedin = "LinkedIn",
                website = "Website",
                bio = "Bio"
            )
        )
    )
)

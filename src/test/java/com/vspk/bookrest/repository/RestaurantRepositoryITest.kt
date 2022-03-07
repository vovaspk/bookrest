package com.vspk.bookrest.repository

import com.vspk.bookrest.AbstractContainerITest
import com.vspk.bookrest.domain.Restaurant
import com.vspk.bookrest.domain.RestaurantThumbnail
import com.vspk.bookrest.domain.Review
import com.vspk.bookrest.domain.Role
import com.vspk.bookrest.domain.Schedule
import com.vspk.bookrest.domain.Tag
import com.vspk.bookrest.domain.User
import javax.transaction.Transactional
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
open class RestaurantRepositoryITest(
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val thumbnailRepository: RestaurantThumbnailRepository,
    @Autowired val scheduleRepository: ScheduleRepository,
    @Autowired val tagRepository: TagRepository,
    @Autowired val roleRepository: RoleRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val reviewRepository: ReviewRepository,
) : AbstractContainerITest() {


    @Test
    @Transactional
    open fun shouldSaveRestaurant() {
        val thumbnail = thumbnailRepository.save(RestaurantThumbnail(path = "src/test/java/com/vspk/bookrest/rest_thumbnails"))
        val schedule = scheduleRepository.save(Schedule(
            monday = "10:00-12:00",
            tuesday = "10:00-12:00",
            wednesday = "10:00-12:00",
            thursday = "10:00-12:00",
            friday = "10:00-12:00",
            saturday = "10:00-12:00",
            sunday = "10:00-12:00"
        ))
        val restaurantToSave = Restaurant(
            "piyana vyshnya",
            null,
            "0973589391",
            thumbnail,
            0.0,
            null,
            "Soborna St, 58, Vinnytsia, Vinnytsia Oblast, 21000",
            null,
            "European",
            schedule,
            emptyList(),
            null,
            emptyList()
        )
//-------------RESTAURANT TAGS---------------------------------
        val tags = listOf(Tag( "cool"), Tag("4dinner"))
        tagRepository.saveAll(tags)
        restaurantToSave.tags = tags
//-----------------USER REVIEWS------------------------------
        val role_user = roleRepository.findByName("ROLE_USER");
        val user = userRepository.save(User(
            username = "vova",
            email = "vova@gmail.com",
            password = "qwe123",
            roles = listOf(role_user),
            likedRestaurants = emptyList(),
            verificationTimesAsked = 1))

//PREVIOS STEPS NEEDS TO BE DONE BEFORE SAVING RESTURANT
        val savedRestaurantt = restaurantRepository.save(restaurantToSave)
//SAVING RESTAURANT
        val review = Review(text = "good place", user = user, rating = 4.5, restaurant = savedRestaurantt, likes = 5)
        reviewRepository.save(review)
        savedRestaurantt.reviews = listOf(review)


        val savedRestaurant = restaurantRepository.findById(savedRestaurantt.id!!).get()

        assertEquals(savedRestaurant.name, "piyana vyshnya");
        assertEquals(savedRestaurant.phone, "0973589391");
        assertEquals(savedRestaurant.thumbnail.id, thumbnail.id);
        assertEquals(savedRestaurant.thumbnail.path, thumbnail.path);
        assertEquals(savedRestaurant.schedule.id, schedule.id);
        assertEquals(savedRestaurant.schedule.monday, schedule.monday);
        assertEquals(savedRestaurant.schedule.tuesday, schedule.tuesday);
        assertEquals(savedRestaurant.schedule.wednesday, schedule.wednesday);
        assertEquals(savedRestaurant.schedule.thursday, schedule.thursday);
        assertEquals(savedRestaurant.schedule.friday, schedule.friday);
        assertEquals(savedRestaurant.schedule.saturday, schedule.saturday);
        assertEquals(savedRestaurant.schedule.sunday, schedule.sunday);
        assertEquals(savedRestaurant.address, "Soborna St, 58, Vinnytsia, Vinnytsia Oblast, 21000");
        assertEquals(savedRestaurant.cuisineType, "European");
        assertEquals(savedRestaurant.tags!!.size, 2)
        assertEquals(savedRestaurant.tags!![0].text, "cool")
        assertEquals(savedRestaurant.tags!![1].text, "4dinner")
        assertEquals(savedRestaurant.reviews!!.size, 1)
        assertEquals(savedRestaurant.reviews!![0].text, "good place")
        assertEquals(savedRestaurant.reviews!![0].user.id, user.id)
        assertEquals(savedRestaurant.reviews!![0].restaurant.id, savedRestaurant.id)

    }

}
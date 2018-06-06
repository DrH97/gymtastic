<?php

use Faker\Generator as Faker;

$factory->define(App\Workout::class, function (Faker $faker) {
    return [
        //
        'workout_date' => $faker->date,
        'exercise_type' => $faker->word,
        'reps' => $faker->numberBetween(1, 12),
        'sets' => $faker->numberBetween(1, 5),
    ];
});
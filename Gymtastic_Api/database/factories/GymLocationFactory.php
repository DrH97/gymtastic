<?php

use Faker\Generator as Faker;

$factory->define(App\GymLocation::class, function (Faker $faker) {
    return [
        //
        'name' => $faker->city,
        'opening_time' => $faker->time,
        'closing_time' => $faker->time,
        'lat' => $faker->latitude,
        'long' => $faker->longitude,
    ];
});
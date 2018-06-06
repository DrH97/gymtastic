<?php

use Faker\Generator as Faker;

$factory->define(App\Instructor::class, function (Faker $faker) {
    return [
        //
        'name' => $faker->name,
        'contacts' => $faker->phoneNumber,
        'email' => $faker->unique()->safeEmail,
        'photo' => $faker->url,
        'gender' => $faker->randomElement(array('M', 'F')),
    ];
});
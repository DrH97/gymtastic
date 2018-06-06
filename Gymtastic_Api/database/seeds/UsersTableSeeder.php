<?php

use Illuminate\Database\Seeder;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        factory('App\User', 3)->create()->each(function($u) {
            $u->workouts()->save(factory(App\Workout::class)->make());
        });
    }
}

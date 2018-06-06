<?php

use Illuminate\Database\Seeder;

class GymLocationsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        factory(App\GymLocation::class, 10)->create()->each(function($g) {
            $instructors = rand(0, 2);
            $g->instructors()->save(factory(App\Instructor::class)->make());
        });
    }
}

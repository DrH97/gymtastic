<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        $this->call(GymLocationsTableSeeder::class);
        $this->call(UsersTableSeeder::class);
        // $this->call(InstructorsTableSeeder::class);
        // $this->call(WorkoutsTableSeeder::class);
    }
}

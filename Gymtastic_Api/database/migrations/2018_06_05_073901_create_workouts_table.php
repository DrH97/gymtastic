<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateWorkoutsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('workouts_92879', function (Blueprint $table) {
            $table->increments('id');
            $table->unsignedInteger('user_id')->nullable();
            $table->unsignedInteger('location_id')->nullable();
            $table->date('workout_date');
            $table->string('exercise_type');
            $table->integer('reps');
            $table->integer('sets');
            $table->timestamps();

            $table->foreign('location_id')
            ->references('id')->on('gym_locations_92879')
            ->onDelete('set null')->onUpdate('cascade');

            $table->foreign('user_id')
            ->references('id')->on('users_92879')
            ->onDelete('set null')->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('workouts_92879');
    }
}

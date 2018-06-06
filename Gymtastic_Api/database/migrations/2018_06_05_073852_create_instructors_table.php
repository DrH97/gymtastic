<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateInstructorsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('instructors_92879', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->string('contacts');
            $table->string('email')->unique();
            $table->string('photo');
            $table->string('gender');
            $table->unsignedInteger('gym_location_id')->nullable();
            $table->timestamps();

            $table->foreign('gym_location_id')
            ->references('id')->on('gym_locations_92879')
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
        Schema::dropIfExists('instructors_92879');
    }
}

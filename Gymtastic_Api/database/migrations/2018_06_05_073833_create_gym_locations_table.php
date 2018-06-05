<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateGymLocationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('gym_locations_92879', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->time('opening_time');
            $table->time('closing_time');
            $table->decimal('lat', 10, 8);
            $table->decimal('long', 10, 8);
            $table->string('days')->nullable();
            // $table->json('days');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('gym_locations_92879');
    }
}

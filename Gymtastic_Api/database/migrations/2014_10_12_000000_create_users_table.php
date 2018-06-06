<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users_92879', function (Blueprint $table) {
            $table->increments('id');
            $table->string('firstname');
            $table->string('lastname');
            $table->string('email')->unique();
            $table->string('password');
        // nullables/defaults
            $table->unsignedInteger('gym_location_id')->nullable();
            $table->integer('pin')->nullable();
            $table->string('photo')->nullable();
            $table->integer('age')->nullable();
            $table->string('gender')->nullable();
            $table->decimal('weight', 5, 2)->nullable();
            $table->decimal('target_weight', 5, 2)->nullable();
            $table->rememberToken();
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
        Schema::dropIfExists('users_92879');
    }
}

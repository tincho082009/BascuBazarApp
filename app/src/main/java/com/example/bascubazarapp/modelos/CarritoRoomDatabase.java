package com.example.bascubazarapp.modelos;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, entities = {CarritoEntity.class}, exportSchema = false)
public abstract class CarritoRoomDatabase extends RoomDatabase {
    abstract public CarritoDao getCarritoDao();
    private static volatile CarritoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CarritoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CarritoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CarritoRoomDatabase.class, "carrito_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }
   private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                CarritoDao dao = INSTANCE.getCarritoDao();
                dao.borrarTodo();

                CarritoEntity ca = new CarritoEntity(1, 2);
                dao.agregarProductoCarrito(ca);
            });
        }
    };
}

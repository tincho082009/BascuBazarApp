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
    private static CarritoRoomDatabase dbCarritoInstance;

    public static synchronized CarritoRoomDatabase getIntance(Context context) {
        if (dbCarritoInstance == null) {
            dbCarritoInstance = Room.databaseBuilder(context.getApplicationContext(),
                            CarritoRoomDatabase.class, "carrito_database")
                            .build();
        }
        return dbCarritoInstance;
    }

    public abstract CarritoDao getCarritoDao();

   /*private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
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

    */
}

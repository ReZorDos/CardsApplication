package ru.kpfu.itis.service;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.UserResponseDto;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class MockService {

    private static final Random random = new Random();

    private static final String[] SURNAMES = {"Иванов", "Петров", "Сидоров", "Кузнецов", "Смирнов"};
    private static final String[] NAMES = {"Иван", "Петр", "Сергей", "Алексей", "Дмитрий"};
    private static final String[] PATRONYMICS = {"Иванович", "Петрович", "Сергеевич", "Алексеевич", "Дмитриевич"};

    public static String generateFIO() {
        String surname = SURNAMES[random.nextInt(SURNAMES.length)];
        String name = NAMES[random.nextInt(NAMES.length)];
        String patronymic = PATRONYMICS[random.nextInt(PATRONYMICS.length)];
        return surname + " " + name + " " + patronymic;
    }

    public Optional<UserResponseDto> getUser() {
        return Optional.of(UserResponseDto.builder()
                        .id(UUID.randomUUID())
                        .fio(generateFIO())
                        .phone(String.valueOf(Math.abs(random.nextInt()*random.nextInt())%10000000 + 1230000000))
                .build());
    }

}

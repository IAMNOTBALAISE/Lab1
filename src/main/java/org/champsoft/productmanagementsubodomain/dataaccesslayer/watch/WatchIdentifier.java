package org.champsoft.productmanagementsubodomain.dataaccesslayer.watch;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class WatchIdentifier {

    @Column(name = "watch_id",nullable = false,unique = true)
    private String watchId;

    public  WatchIdentifier(String watchId) {
        this.watchId = (watchId != null && !watchId.isEmpty()) ? watchId : generateRandomWatchId();
    }

    public String generateRandomWatchId() {

        return UUID.randomUUID().toString().replace("-", "").substring(0, 17).toUpperCase();
    }
}

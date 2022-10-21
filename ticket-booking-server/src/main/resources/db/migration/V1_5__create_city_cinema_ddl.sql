CREATE TABLE IF NOT EXISTS TicketBooking.City (
            Id CHAR(36) NOT NULL,
            Name CHAR(255) UNIQUE NOT NULL,
            State CHAR(255) NOT NULL,
            ZipCode CHAR(255) NULL,
            CONSTRAINT PK_TicketBooking_City_Id PRIMARY KEY CLUSTERED (Id ASC),
            INDEX IDX_TicketBooking_City_Name (Name));

CREATE TABLE IF NOT EXISTS TicketBooking.Cinema (
            Id CHAR(36) NOT NULL,
            Name CHAR(255) NOT NULL,
            TotalCinemaHalls INT,
            CityId CHAR(36) NOT NULL,
            CONSTRAINT PK_TicketBooking_Cinema_Id PRIMARY KEY CLUSTERED (Id ASC),
            INDEX IDX_TicketBooking_Cinema_Name (Name),
            FOREIGN KEY (CityId) REFERENCES TicketBooking.City(Id) ON DELETE CASCADE,
            UNIQUE KEY UK_TicketBooking_CityId_Name (CityId, Name));

CREATE TABLE IF NOT EXISTS TicketBooking.CinemaHall (
            Id CHAR(36) NOT NULL,
            Name CHAR(255) NOT NULL,
            TotalSeats INT,
            CinemaId CHAR(36) NOT NULL,
            CONSTRAINT PK_TicketBooking_CinemaHall_Id PRIMARY KEY CLUSTERED (Id ASC),
            INDEX IDX_TicketBooking_CinemaHall_Name (Name),
            FOREIGN KEY (CinemaId) REFERENCES TicketBooking.Cinema(Id) ON DELETE CASCADE,
            UNIQUE KEY UK_TicketBooking_CinemaId_Name (CinemaId, Name));

CREATE TABLE IF NOT EXISTS TicketBooking.CinemaHallSeat (
            Id CHAR(36) NOT NULL,
            SeatRow INT NOT NULL,
            SeatColumn INT NOT NULL,
            SeatType CHAR(16) NOT NULL,
            CinemaHallId CHAR(36) NOT NULL,
            CONSTRAINT PK_TicketBooking_CinemaHallSeat_Id PRIMARY KEY CLUSTERED (Id ASC),
            INDEX IDX_TicketBooking_CinemaHallSeat_Row_Col (SeatRow, SeatColumn),
            FOREIGN KEY (CinemaHallId) REFERENCES TicketBooking.CinemaHall(Id) ON DELETE CASCADE,
            UNIQUE KEY UK_TicketBooking_CinemaHallId_SeatRow_SeatColumn (CinemaHallId, SeatRow, SeatColumn));

CREATE TABLE IF NOT EXISTS TicketBooking.Show (
            Id CHAR(36) NOT NULL,
            StartTime DATETIME NOT NULL,
            EndTime DATETIME NOT NULL,
            MovieId CHAR(36) NOT NULL,
            CONSTRAINT PK_TicketBooking_Show_Id PRIMARY KEY CLUSTERED (Id ASC));

CREATE TABLE IF NOT EXISTS TicketBooking.ShowSeat (
            Id CHAR(36) NOT NULL,
            SeatNumber INT NOT NULL,
            IsReserved TINYINT NOT NULL DEFAULT 0,
            Price Double(10, 2) NOT NULL,
            SeatRow INT NOT NULL,
            SeatColumn INT NOT NULL,
            SeatType CHAR(16) NOT NULL,
            ShowId CHAR(36) NOT NULL,
            CONSTRAINT PK_TicketBooking_ShowSeat_Id PRIMARY KEY CLUSTERED (Id ASC),
            INDEX IDX_TicketBooking_ShowSeat_SeatNumber (SeatNumber),
            FOREIGN KEY (ShowId) REFERENCES TicketBooking.Show(Id) ON DELETE CASCADE,
            UNIQUE KEY UK_TicketBooking_ShowId_SeatNumber (ShowId, SeatNumber));


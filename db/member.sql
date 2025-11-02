USE [master]
GO
/****** Object:  Database [gourmetmap]    Script Date: 2025/10/17 下午 01:20:19 ******/
CREATE DATABASE [gourmetmap]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'gourmetmap', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\gourmetmap.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'gourmetmap_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\gourmetmap_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [gourmetmap] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [gourmetmap].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [gourmetmap] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [gourmetmap] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [gourmetmap] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [gourmetmap] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [gourmetmap] SET ARITHABORT OFF 
GO
ALTER DATABASE [gourmetmap] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [gourmetmap] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [gourmetmap] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [gourmetmap] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [gourmetmap] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [gourmetmap] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [gourmetmap] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [gourmetmap] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [gourmetmap] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [gourmetmap] SET  ENABLE_BROKER 
GO
ALTER DATABASE [gourmetmap] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [gourmetmap] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [gourmetmap] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [gourmetmap] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [gourmetmap] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [gourmetmap] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [gourmetmap] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [gourmetmap] SET RECOVERY FULL 
GO
ALTER DATABASE [gourmetmap] SET  MULTI_USER 
GO
ALTER DATABASE [gourmetmap] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [gourmetmap] SET DB_CHAINING OFF 
GO
ALTER DATABASE [gourmetmap] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [gourmetmap] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [gourmetmap] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [gourmetmap] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'gourmetmap', N'ON'
GO
ALTER DATABASE [gourmetmap] SET QUERY_STORE = ON
GO
ALTER DATABASE [gourmetmap] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [gourmetmap]
GO
/****** Object:  Table [dbo].[Accounts]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Accounts](
	[AccountID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [bigint] NULL,
	[Account] [varchar](255) NOT NULL,
	[Password] [varchar](255) NULL,
	[VerificationToken] [varchar](255) NULL,
	[IsVerified] [bit] NULL,
	[CreatedAt] [datetime2](6) NULL,
	[LastLogIn] [datetime] NULL,
	[Status] [int] NULL,
 CONSTRAINT [PK_Accounts] PRIMARY KEY CLUSTERED 
(
	[AccountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AD_Data]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AD_Data](
	[ADID] [int] IDENTITY(1,1) NOT NULL,
	[PlanID] [int] NOT NULL,
	[VendorID] [int] NOT NULL,
	[CampaignName] [nvarchar](100) NOT NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
	[AD_Status] [nvarchar](10) NOT NULL,
	[PIC_URL] [nvarchar](max) NULL,
 CONSTRAINT [PK_AD_Data] PRIMARY KEY CLUSTERED 
(
	[ADID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AD_Plans]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AD_Plans](
	[PlanID] [int] IDENTITY(1,1) NOT NULL,
	[PlanName] [nvarchar](50) NOT NULL,
	[PlanPrice] [int] NOT NULL,
	[Days] [int] NOT NULL,
	[PlanDescription] [nvarchar](1000) NOT NULL,
 CONSTRAINT [PK_AD_Plans] PRIMARY KEY CLUSTERED 
(
	[PlanID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BlackLists]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BlackLists](
	[MemberID] [int] NOT NULL,
	[Reason] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_BlackLists_1] PRIMARY KEY CLUSTERED 
(
	[MemberID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Carts]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Carts](
	[CartID] [int] IDENTITY(1,1) NOT NULL,
	[MemberID] [int] NOT NULL,
	[ProductID] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
 CONSTRAINT [PK_Carts] PRIMARY KEY CLUSTERED 
(
	[CartID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Comments]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comments](
	[CommentID] [int] NOT NULL,
	[MemberID] [int] NOT NULL,
	[OrderDetailID] [int] NOT NULL,
	[Context] [nvarchar](500) NOT NULL,
	[CreateAt] [datetime] NOT NULL,
 CONSTRAINT [PK_Comments] PRIMARY KEY CLUSTERED 
(
	[CommentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Coupons]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Coupons](
	[CouponID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](20) NOT NULL,
	[PlanID] [int] NULL,
	[VendorID] [int] NULL,
	[PrecentageDiscount] [float] NULL,
	[PriceDiscount] [int] NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
 CONSTRAINT [PK_Coupons] PRIMARY KEY CLUSTERED 
(
	[CouponID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CustomerServiceID]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CustomerServiceID](
	[CustomerServiceID] [int] IDENTITY(1,1) NOT NULL,
	[Email] [nvarchar](30) NULL,
	[MemberID] [int] NOT NULL,
	[VendorID] [int] NOT NULL,
	[Context] [nvarchar](300) NULL,
	[CS_Status] [nvarchar](10) NULL,
	[CreateAt] [datetime] NULL,
 CONSTRAINT [PK_CustomerServiceID] PRIMARY KEY CLUSTERED 
(
	[CustomerServiceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CustomerServicesReplies]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CustomerServicesReplies](
	[CustomerServiceReplyID] [int] IDENTITY(1,1) NOT NULL,
	[CustomerServiceID] [int] NOT NULL,
	[AccountID] [int] NOT NULL,
	[Context] [nvarchar](300) NOT NULL,
	[CreateAt] [datetime] NOT NULL,
 CONSTRAINT [PK_CustomerServicesReplies] PRIMARY KEY CLUSTERED 
(
	[CustomerServiceReplyID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FAQ]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FAQ](
	[FAQID] [int] IDENTITY(1,1) NOT NULL,
	[Keyword] [nvarchar](10) NULL,
	[Answer] [nvarchar](100) NULL,
 CONSTRAINT [PK_FAQ] PRIMARY KEY CLUSTERED 
(
	[FAQID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Favorites]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Favorites](
	[FavoriteID] [int] IDENTITY(1,1) NOT NULL,
	[MemberID] [int] NOT NULL,
	[VendorID] [int] NOT NULL,
 CONSTRAINT [PK_Favorites] PRIMARY KEY CLUSTERED 
(
	[FavoriteID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MemberLookHistroies]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MemberLookHistroies](
	[LookHistoryID] [int] IDENTITY(1,1) NOT NULL,
	[MemberID] [int] NOT NULL,
	[VendorID] [int] NOT NULL,
	[CreateAt] [datetime] NOT NULL,
 CONSTRAINT [PK_MemberLookHistroies] PRIMARY KEY CLUSTERED 
(
	[LookHistoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Members]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Members](
	[MemberID] [int] IDENTITY(1,1) NOT NULL,
	[UserName] [nvarchar](30) NOT NULL,
	[AvatarURL] [nvarchar](1000) NULL,
	[Gender] [nvarchar](10) NULL,
	[Birthdate] [date] NOT NULL,
	[Phone] [nvarchar](20) NULL,
	[City] [nvarchar](10) NOT NULL,
	[District] [nvarchar](10) NOT NULL,
	[AccountID] [int] NOT NULL,
 CONSTRAINT [PK_Members] PRIMARY KEY CLUSTERED 
(
	[MemberID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Members1]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Members1](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[email] [nvarchar](100) NOT NULL,
	[role] [nvarchar](20) NULL,
	[verification_token] [nvarchar](100) NULL,
	[is_verified] [bit] NULL,
	[created_at] [datetime] NULL,
	[avatar_url] [nvarchar](255) NULL,
	[nickname] [nvarchar](50) NULL,
	[gender] [nvarchar](10) NULL,
	[birthdate] [date] NULL,
	[phone] [char](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[OrderDetailID] [int] IDENTITY(1,1) NOT NULL,
	[OrderID] [int] NOT NULL,
	[ProductID] [int] NULL,
	[ADID] [int] NULL,
	[Quantity] [int] NOT NULL,
	[UnitPrice] [int] NOT NULL,
	[ExpireDate] [date] NULL,
 CONSTRAINT [PK_OrderDetails] PRIMARY KEY CLUSTERED 
(
	[OrderDetailID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[OrderID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[OrderDate] [datetime] NOT NULL,
	[BounsPoint] [int] NULL,
	[PaymentID] [int] NOT NULL,
	[CouponID] [int] NULL,
 CONSTRAINT [PK_Orders] PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Payments]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Payments](
	[PaymentID] [int] IDENTITY(1,1) NOT NULL,
	[PaymentMethod] [nvarchar](10) NOT NULL,
	[PaymentStatus] [nvarchar](10) NOT NULL,
	[TransactionID] [varchar](100) NOT NULL,
	[PaymentDate] [datetime] NOT NULL,
 CONSTRAINT [PK_Payments] PRIMARY KEY CLUSTERED 
(
	[PaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PD_Categories]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PD_Categories](
	[PD_CategoryID] [int] IDENTITY(1,1) NOT NULL,
	[CategoryName] [nvarchar](100) NOT NULL,
	[Description] [nvarchar](1000) NULL,
 CONSTRAINT [PK_PD_Categories] PRIMARY KEY CLUSTERED 
(
	[PD_CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[ProductID] [int] IDENTITY(1,1) NOT NULL,
	[ProductName] [nvarchar](100) NOT NULL,
	[PD_CategoryID] [int] NOT NULL,
	[VendorID] [int] NOT NULL,
	[UnitPrice] [int] NOT NULL,
	[SpecialPrice] [int] NULL,
	[SpecialDiscount] [float] NULL,
	[EndDate] [date] NOT NULL,
	[Stock] [int] NOT NULL,
 CONSTRAINT [PK_Products] PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Rates]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rates](
	[RateID] [int] IDENTITY(1,1) NOT NULL,
	[RateCategory] [nvarchar](10) NOT NULL,
	[MemberID] [int] NOT NULL,
	[OrderDetailID] [int] NOT NULL,
	[CreateAt] [datetime] NOT NULL,
 CONSTRAINT [PK_Rates] PRIMARY KEY CLUSTERED 
(
	[RateID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reservations]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reservations](
	[ReservationID] [int] IDENTITY(1,1) NOT NULL,
	[MemberID] [int] NOT NULL,
	[VendorID] [int] NOT NULL,
	[ReservationDate] [date] NOT NULL,
	[ReservationPeriod] [nvarchar](20) NOT NULL,
	[GuestCount] [smallint] NOT NULL,
	[RV_Status] [nchar](10) NOT NULL,
	[CreateTime] [datetime] NOT NULL,
	[UpdateTime] [datetime] NULL,
 CONSTRAINT [PK_Reservations] PRIMARY KEY CLUSTERED 
(
	[ReservationID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RS_Capabilities]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RS_Capabilities](
	[VendorID] [int] NOT NULL,
	[ReservationDate] [nchar](10) NOT NULL,
	[ReservationPeriod] [nchar](10) NOT NULL,
	[Capability] [int] NOT NULL,
 CONSTRAINT [PK_RS_Capabilities] PRIMARY KEY CLUSTERED 
(
	[VendorID] ASC,
	[ReservationDate] ASC,
	[ReservationPeriod] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Type]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Type](
	[UserID] [bigint] NOT NULL,
	[Description] [varchar](255) NULL,
	[Type] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Type] [varchar](255) NULL,
	[Description] [varchar](255) NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserType]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserType](
	[UserID] [bigint] NOT NULL,
	[Description] [varchar](255) NULL,
	[Type] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VD_Categories]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VD_Categories](
	[VD_CategoryID] [int] IDENTITY(1,1) NOT NULL,
	[CategoryName] [nvarchar](30) NOT NULL,
	[Description] [nvarchar](300) NULL,
 CONSTRAINT [PK_RT_Categories] PRIMARY KEY CLUSTERED 
(
	[VD_CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VD_Details]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VD_Details](
	[VD_DetailID] [int] IDENTITY(1,1) NOT NULL,
	[VendorID] [int] NOT NULL,
	[VD_OpeningHourID] [int] NULL,
	[PriceRange] [nvarchar](20) NOT NULL,
	[VD_StyleID] [int] NOT NULL,
	[SeatsNumber] [int] NOT NULL,
	[AirConditioner] [bit] NOT NULL,
	[Park] [bit] NOT NULL,
	[BabyFriendly] [bit] NOT NULL,
	[PetFriendly] [bit] NOT NULL,
	[VeganlFriendly] [bit] NOT NULL,
	[HalalFriendly] [bit] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VD_OpeningHours]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VD_OpeningHours](
	[VD_OpeningHourID] [int] IDENTITY(1,1) NOT NULL,
	[VendorID] [int] NOT NULL,
	[DayOfWeek] [int] NOT NULL,
	[OpeningTime] [time](7) NOT NULL,
	[ClosingTime] [time](7) NOT NULL,
 CONSTRAINT [PK_RT_OpeningHours] PRIMARY KEY CLUSTERED 
(
	[VD_OpeningHourID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VD_Styles]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VD_Styles](
	[VD_StyleID] [int] IDENTITY(1,1) NOT NULL,
	[StyleName] [nvarchar](20) NOT NULL,
	[Description] [nvarchar](300) NULL,
 CONSTRAINT [PK_RT_Styles] PRIMARY KEY CLUSTERED 
(
	[VD_StyleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Vendors]    Script Date: 2025/10/17 下午 01:20:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Vendors](
	[VendorID] [int] IDENTITY(1,1) NOT NULL,
	[VendorName] [nvarchar](50) NOT NULL,
	[TaxID] [smallint] NULL,
	[OwnerName] [nvarchar](100) NOT NULL,
	[ContactName] [nvarchar](30) NOT NULL,
	[ContactTitle] [nvarchar](30) NOT NULL,
	[ContactTel] [nvarchar](30) NOT NULL,
	[ContactEmail] [nvarchar](30) NOT NULL,
	[Address] [nvarchar](200) NOT NULL,
	[VD_CategoryID] [int] NOT NULL,
	[LogoURL] [nvarchar](max) NULL,
	[VD_Status] [nvarchar](10) NOT NULL,
	[AccountID] [int] NOT NULL,
 CONSTRAINT [PK_Vendors] PRIMARY KEY CLUSTERED 
(
	[VendorID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Accounts] ON 

INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (1, 3, N'Text@gururu.com.tw', N'123456', N'5AC0FFD0-EDBD-4C09-8732-9409B8C1AE4D', 1, NULL, CAST(N'2025-09-25T20:11:41.837' AS DateTime), 0)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (4, 2, N'alice1@gururu.com.tw', N'Passw0rd02', N'353cb439-e6e5-41c5-ac78-d76e08ca9c61', 1, NULL, CAST(N'2025-10-17T10:34:22.863' AS DateTime), 1)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (5, 2, N'bob2@gururu.com.tw', N'Passw0rd02', N'A2E5BADC-EF2E-4AED-BC98-3DD7C927F0FC', 1, NULL, CAST(N'2025-09-28T15:24:33.533' AS DateTime), 2)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (6, 2, N'charlie3@gururu.com.tw', N'Passw0rd03', N'CD5B6874-8313-4ED2-8712-614147E5DFB5', 1, NULL, CAST(N'2025-10-16T15:20:53.670' AS DateTime), 3)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (7, 2, N'david4@gururu.com.tw', N'Passw0rd04', N'B18C4396-9650-4001-977E-192E7368F7A4', 1, NULL, CAST(N'2025-09-10T22:49:59.287' AS DateTime), 1)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (8, 2, N'eva5@gururu.com.tw', N'Passw0rd05', N'2FFBFB3B-6BE6-44CD-B40C-CCD6A3E9D031', 1, NULL, CAST(N'2025-08-26T16:45:20.000' AS DateTime), 2)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (9, 2, N'frank6@gururu.com.tw', N'Passw0rd06', N'DD8E8EEA-4352-4771-A975-D825E6744B37', 1, NULL, CAST(N'2025-10-14T17:18:14.763' AS DateTime), 3)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (10, 2, N'grace7@gururu.com.tw', N'Passw0rd07', N'D53321E9-4650-496C-B7D8-C7852213BDC1', 1, NULL, CAST(N'2025-10-16T15:20:19.727' AS DateTime), 1)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (11, 2, N'henry8@gururu.com.tw', N'Passw0rd08', N'A3786A73-1C3F-4CE8-9097-94DC019D3030', 1, NULL, CAST(N'2025-10-02T09:05:37.777' AS DateTime), 2)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (12, 2, N'irene9@gururu.com.tw', N'Passw0rd09', N'0E4C7B0D-9B29-472D-83FD-EBF8C9CDCC34', 1, NULL, CAST(N'2025-08-26T16:45:20.000' AS DateTime), 3)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (13, 2, N'jack10@gururu.com.tw', N'Passw0rd10', N'DAF5C742-A845-4E0B-A302-1804B0773DFB', 1, NULL, CAST(N'2025-10-14T16:18:03.793' AS DateTime), 1)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (4078, 1, N'admin@gururu.com.tw', N'Admin@12345', N'7CE8854C-95CE-43AA-80B2-C2B0BA7B8AA0', 1, CAST(N'2025-09-19T01:17:55.5766670' AS DateTime2), CAST(N'2025-10-16T16:55:19.927' AS DateTime), 1)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (4080, 2, N'skywalkerdemo666@gmail.com', N'111112', N'23f07462-ebdb-44af-92c8-259a4d74906c', 1, CAST(N'2025-09-25T19:34:08.0356660' AS DateTime2), CAST(N'2025-10-17T13:12:31.793' AS DateTime), 1)
INSERT [dbo].[Accounts] ([AccountID], [UserID], [Account], [Password], [VerificationToken], [IsVerified], [CreatedAt], [LastLogIn], [Status]) VALUES (4081, 3, N'contact20@gururu.com.tw', N'Passw0rd21', N'fcfe7afc-53b4-4c82-a9be-c8e9121c8190', 0, CAST(N'2025-10-01T09:04:09.2963610' AS DateTime2), CAST(N'2025-10-13T10:54:49.110' AS DateTime), 0)
SET IDENTITY_INSERT [dbo].[Accounts] OFF
GO
SET IDENTITY_INSERT [dbo].[Favorites] ON 

INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (1, 1, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (2, 1, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (3, 2, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (4, 2, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (5, 2, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (6, 3, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (7, 3, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (8, 4, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (9, 4, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (10, 5, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (11, 5, 9)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (12, 6, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (13, 6, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (14, 7, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (15, 7, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (16, 8, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (17, 8, 8)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (18, 9, 8)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (19, 9, 10)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (20, 10, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (21, 10, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (22, 11, 9)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (23, 11, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (24, 12, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (25, 12, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (26, 12, 10)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (27, 1, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (28, 1, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (29, 1, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (30, 1, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (31, 1, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (32, 1, 8)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (33, 2, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (34, 2, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (35, 2, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (36, 2, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (37, 2, 8)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (38, 2, 9)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (39, 2, 10)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (40, 3, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (41, 3, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (42, 3, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (43, 3, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (44, 3, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (45, 4, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (46, 4, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (47, 4, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (48, 4, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (49, 4, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (50, 4, 9)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (51, 5, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (52, 5, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (53, 5, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (54, 5, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (55, 5, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (56, 5, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (57, 5, 8)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (58, 5, 10)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (59, 6, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (60, 6, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (61, 6, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (62, 6, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (63, 6, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (64, 7, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (65, 7, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (66, 7, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (67, 7, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (68, 7, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (69, 7, 8)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (70, 7, 9)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (71, 7, 10)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (72, 7, 11)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (73, 8, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (74, 8, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (75, 8, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (76, 8, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (77, 8, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (78, 8, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (79, 8, 9)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (80, 9, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (81, 9, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (82, 9, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (83, 9, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (84, 9, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (85, 9, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (86, 10, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (87, 10, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (88, 10, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (89, 10, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (90, 10, 8)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (91, 11, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (92, 11, 2)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (93, 11, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (94, 11, 5)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (95, 11, 6)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (96, 12, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (97, 12, 3)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (98, 12, 4)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (99, 12, 6)
GO
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (100, 12, 7)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (104, 2, 1)
INSERT [dbo].[Favorites] ([FavoriteID], [MemberID], [VendorID]) VALUES (105, 2, 1)
SET IDENTITY_INSERT [dbo].[Favorites] OFF
GO
SET IDENTITY_INSERT [dbo].[MemberLookHistroies] ON 

INSERT [dbo].[MemberLookHistroies] ([LookHistoryID], [MemberID], [VendorID], [CreateAt]) VALUES (1, 2, 1, CAST(N'2025-09-28T15:19:19.753' AS DateTime))
INSERT [dbo].[MemberLookHistroies] ([LookHistoryID], [MemberID], [VendorID], [CreateAt]) VALUES (2, 2, 2, CAST(N'2025-09-28T15:19:47.357' AS DateTime))
INSERT [dbo].[MemberLookHistroies] ([LookHistoryID], [MemberID], [VendorID], [CreateAt]) VALUES (3, 2, 3, CAST(N'2025-09-28T15:24:06.457' AS DateTime))
INSERT [dbo].[MemberLookHistroies] ([LookHistoryID], [MemberID], [VendorID], [CreateAt]) VALUES (4, 3, 2, CAST(N'2025-09-28T15:24:43.303' AS DateTime))
INSERT [dbo].[MemberLookHistroies] ([LookHistoryID], [MemberID], [VendorID], [CreateAt]) VALUES (5, 2, 10, CAST(N'2025-10-03T15:24:51.823' AS DateTime))
INSERT [dbo].[MemberLookHistroies] ([LookHistoryID], [MemberID], [VendorID], [CreateAt]) VALUES (6, 2, 21, CAST(N'2025-10-13T14:09:54.213' AS DateTime))
SET IDENTITY_INSERT [dbo].[MemberLookHistroies] OFF
GO
SET IDENTITY_INSERT [dbo].[Members] ON 

INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (1, N'Text', NULL, NULL, CAST(N'2020-01-01' AS Date), NULL, N'台南市', N'永康區', 1)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (2, N'王大明', N'6851af2f-9800-4479-8523-2d293be40a01.jpg', N'男', CAST(N'1995-10-26' AS Date), N'0912345666', N'台南市', N'南區', 4)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (3, N'陳美玲', NULL, NULL, CAST(N'1985-09-23' AS Date), NULL, N'新北市', N'板橋區', 5)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (4, N'林建宏', NULL, NULL, CAST(N'1992-11-07' AS Date), NULL, N'台中市', N'西屯區', 6)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (5, N'張雅婷', NULL, NULL, CAST(N'1995-03-18' AS Date), NULL, N'高雄市', N'苓雅區', 7)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (6, N'黃志偉', NULL, NULL, CAST(N'1988-07-02' AS Date), NULL, N'台南市', N'東區', 8)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (7, N'劉欣怡', N'b16c3667-dc90-4381-9d1c-d86f9d255da1.jpg', NULL, CAST(N'1993-12-29' AS Date), NULL, N'桃園市', N'中壢區', 9)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (8, N'周冠宇', NULL, NULL, CAST(N'1991-04-15' AS Date), NULL, N'新竹市', N'東區', 10)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (9, N'吳怡君', NULL, NULL, CAST(N'1987-10-05' AS Date), NULL, N'基隆市', N'仁愛區', 11)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (10, N'蔡宗翰', NULL, NULL, CAST(N'1994-01-21' AS Date), NULL, N'嘉義市', N'西區', 12)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (11, N'許慧珍', NULL, NULL, CAST(N'1989-06-30' AS Date), NULL, N'花蓮縣', N'花蓮市', 13)
INSERT [dbo].[Members] ([MemberID], [UserName], [AvatarURL], [Gender], [Birthdate], [Phone], [City], [District], [AccountID]) VALUES (15, N'zzz', N'e036ae36-5e75-403a-a589-48cf87d45a6e.jpg', N'男', CAST(N'2000-01-01' AS Date), N'未填寫', N'新北市', N'板橋區', 4080)
SET IDENTITY_INSERT [dbo].[Members] OFF
GO
SET IDENTITY_INSERT [dbo].[Members1] ON 

INSERT [dbo].[Members1] ([id], [username], [password], [email], [role], [verification_token], [is_verified], [created_at], [avatar_url], [nickname], [gender], [birthdate], [phone]) VALUES (1, N'skywalker13217@gmail.com', N'111', N'skywalker13217@gmail.com', N'admin', NULL, 1, CAST(N'2025-08-26T16:28:08.527' AS DateTime), NULL, NULL, NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Members1] OFF
GO
SET IDENTITY_INSERT [dbo].[OrderDetails] ON 

INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (1, 1, 1, NULL, 2, 85, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (2, 1, 4, NULL, 1, 899, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (3, 2, 2, NULL, 1, 99, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (4, 2, 5, NULL, 1, 1199, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (5, 3, 3, NULL, 2, 120, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (6, 4, 6, NULL, 1, 350, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (7, 4, 7, NULL, 1, 1080, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (8, 5, 8, NULL, 2, 680, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (9, 6, 9, NULL, 1, 520, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (10, 6, 10, NULL, 1, 1150, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (11, 7, 11, NULL, 2, 760, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (12, 8, 12, NULL, 1, 480, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (13, 8, 1, NULL, 1, 85, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (14, 9, 13, NULL, 2, 420, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (15, 10, 1, NULL, 1, 85, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (16, 10, 2, NULL, 1, 99, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (17, 11, 4, NULL, 1, 899, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (18, 12, 6, NULL, 1, 350, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (19, 12, 5, NULL, 1, 1199, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (20, 13, 7, NULL, 1, 1080, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (21, 14, 8, NULL, 1, 680, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (22, 14, 9, NULL, 1, 520, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (23, 15, 10, NULL, 1, 1150, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (24, 16, 11, NULL, 1, 760, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (25, 16, 12, NULL, 1, 480, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (26, 17, 13, NULL, 1, 420, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (27, 17, 3, NULL, 1, 120, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (28, 18, 2, NULL, 2, 99, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (29, 19, 5, NULL, 1, 1199, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (30, 20, 13, NULL, 1, 420, CAST(N'2099-12-31' AS Date))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [ProductID], [ADID], [Quantity], [UnitPrice], [ExpireDate]) VALUES (31, 20, 7, NULL, 1, 1080, CAST(N'2099-12-31' AS Date))
SET IDENTITY_INSERT [dbo].[OrderDetails] OFF
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (1, 2, CAST(N'2025-09-01T10:20:00.000' AS DateTime), 45, 1, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (2, 2, CAST(N'2025-09-02T15:45:00.000' AS DateTime), 30, 2, 1)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (3, 2, CAST(N'2025-09-03T12:30:00.000' AS DateTime), 25, 1, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (4, 2, CAST(N'2025-09-04T18:10:00.000' AS DateTime), 78, 3, 2)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (5, 2, CAST(N'2025-09-05T09:50:00.000' AS DateTime), 60, 2, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (6, 2, CAST(N'2025-09-06T20:00:00.000' AS DateTime), 90, 1, 3)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (7, 2, CAST(N'2025-09-07T13:40:00.000' AS DateTime), 37, 2, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (8, 2, CAST(N'2025-09-08T11:25:00.000' AS DateTime), 110, 3, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (9, 2, CAST(N'2025-09-09T16:00:00.000' AS DateTime), 42, 1, 2)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (10, 2, CAST(N'2025-09-10T19:30:00.000' AS DateTime), 56, 2, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (11, 2, CAST(N'2025-09-11T14:15:00.000' AS DateTime), 30, 1, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (12, 2, CAST(N'2025-09-12T17:55:00.000' AS DateTime), 48, 3, 1)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (13, 2, CAST(N'2025-09-13T10:05:00.000' AS DateTime), 72, 2, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (14, 2, CAST(N'2025-09-14T21:20:00.000' AS DateTime), 26, 1, 3)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (15, 2, CAST(N'2025-09-15T12:10:00.000' AS DateTime), 83, 2, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (16, 2, CAST(N'2025-09-16T08:45:00.000' AS DateTime), 50, 1, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (17, 2, CAST(N'2025-09-17T18:35:00.000' AS DateTime), 64, 3, 2)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (18, 2, CAST(N'2025-09-18T11:55:00.000' AS DateTime), 39, 2, NULL)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (19, 2, CAST(N'2025-09-19T15:25:00.000' AS DateTime), 71, 1, 1)
INSERT [dbo].[Orders] ([OrderID], [UserID], [OrderDate], [BounsPoint], [PaymentID], [CouponID]) VALUES (20, 2, CAST(N'2025-09-20T20:15:00.000' AS DateTime), 45, 3, NULL)
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
SET IDENTITY_INSERT [dbo].[Products] ON 

INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (1, N'酸奶水果優格杯', 1, 1, 90, 85, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (2, N'巧克力脆片餅乾', 1, 1, 100, 99, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (3, N'香煎雞腿便當', 2, 2, 120, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (4, N'雙人火鍋套餐券', 1, 2, 899, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (5, N'日式壽司吃到飽券', 2, 1, 1199, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (6, N'咖啡下午茶套券', 3, 5, 350, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (7, N'西餐牛排雙人券', 1, 3, 1080, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (8, N'韓式炸雞分享券', 2, 4, 680, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (9, N'港式點心雙人券', 3, 2, 520, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (10, N'燒烤暢食餐券', 1, 5, 1150, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (11, N'泰式料理套餐券', 2, 3, 760, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (12, N'義式披薩套餐券', 3, 1, 480, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
INSERT [dbo].[Products] ([ProductID], [ProductName], [PD_CategoryID], [VendorID], [UnitPrice], [SpecialPrice], [SpecialDiscount], [EndDate], [Stock]) VALUES (13, N'早午餐 Brunch 套券', 2, 4, 420, NULL, NULL, CAST(N'2099-12-31' AS Date), 99999)
SET IDENTITY_INSERT [dbo].[Products] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([UserID], [Type], [Description]) VALUES (1, N'管理員', N'administer')
INSERT [dbo].[Users] ([UserID], [Type], [Description]) VALUES (2, N'會員', N'Members')
INSERT [dbo].[Users] ([UserID], [Type], [Description]) VALUES (3, N'廠商', N'Vendors')
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET IDENTITY_INSERT [dbo].[VD_Categories] ON 

INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (1, N'咖啡廳', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (2, N'吃到飽', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (3, N'速食', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (4, N'素食 / 蛋奶素 / 全素', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (5, N'小吃店', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (6, N'攤販 / 夜市', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (7, N'高級餐廳', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (23, N'家庭式餐廳', NULL)
INSERT [dbo].[VD_Categories] ([VD_CategoryID], [CategoryName], [Description]) VALUES (24, N'酒吧 / 居酒屋', NULL)
SET IDENTITY_INSERT [dbo].[VD_Categories] OFF
GO
SET IDENTITY_INSERT [dbo].[VD_Styles] ON 

INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (1, N'早餐', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (2, N'午餐', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (3, N'晚餐', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (4, N'消夜', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (5, N'美式', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (6, N'義式', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (7, N'台式', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (8, N'中式', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (9, N'日式', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (10, N'韓式', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (11, N'泰式', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (12, N'印度料理', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (13, N'火鍋', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (14, N'燒烤', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (15, N'海鮮', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (16, N'牛排館', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (17, N'漢堡 / 三明治', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (18, N'披薩', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (19, N'麵食', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (20, N'飲料', NULL)
INSERT [dbo].[VD_Styles] ([VD_StyleID], [StyleName], [Description]) VALUES (21, N'冰品 / 甜湯', NULL)
SET IDENTITY_INSERT [dbo].[VD_Styles] OFF
GO
SET IDENTITY_INSERT [dbo].[Vendors] ON 

INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (1, N'大安火鍋店', NULL, N'林建宏', N'陳美玲', N'經理', N'02-2365-1234', N'contact1@gururu.com.tw', N'台北市大安區仁愛路三段100號', 1, NULL, N'上限中', 1)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (2, N'板橋壽司屋', NULL, N'王志強', N'李佳玲', N'店長', N'02-2256-4567', N'contact2@gururu.com.tw', N'新北市板橋區文化路一段200號', 2, NULL, N'審核中', 4)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (3, N'西屯咖啡館', NULL, N'張雅雯', N'吳宗翰', N'客服主任', N'04-2456-7890', N'contact3@gururu.com.tw', N'台中市西屯區台灣大道三段150號', 3, NULL, N'上限中', 5)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (4, N'苓雅燒烤坊', NULL, N'陳冠宇', N'何怡君', N'副理', N'07-3345-6789', N'contact4@gururu.com.tw', N'高雄市苓雅區中正一路88號', 4, NULL, N'結束合作', 6)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (5, N'東區牛排館', NULL, N'黃柏成', N'徐欣怡', N'店長', N'06-2098-1122', N'contact5@gururu.com.tw', N'台南市東區府連東路66號', 5, NULL, N'上限中', 7)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (6, N'中壢泰式料理', NULL, N'周冠霖', N'洪怡萱', N'經理', N'03-4221-3344', N'contact6@gururu.com.tw', N'桃園市中壢區中山路120號', 2, NULL, N'審核中', 8)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (7, N'新竹早午餐廳', NULL, N'蔡宗翰', N'鄭慧珍', N'店長', N'03-5234-5566', N'contact7@gururu.com.tw', N'新竹市東區光復路二段88號', 1, NULL, N'上限中', 9)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (8, N'基隆港式點心', NULL, N'許志偉', N'劉雅婷', N'副理', N'02-2423-7788', N'contact8@gururu.com.tw', N'基隆市仁愛區愛三路77號', 3, NULL, N'停用中', 10)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (9, N'嘉義韓式炸雞', NULL, N'吳宗賢', N'陳怡安', N'經理', N'05-2233-4455', N'contact9@gururu.com.tw', N'嘉義市西區中正路55號', 4, NULL, N'上限中', 11)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (10, N'花蓮披薩屋', NULL, N'張宏偉', N'李欣穎', N'店長', N'03-8332-6677', N'contact10@gururu.com.tw', N'花蓮縣花蓮市中華路99號', 5, NULL, N'審核中', 12)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (11, N'南港麵館', NULL, N'林俊宏', N'陳怡如', N'經理', N'02-2655-9988', N'contact11@gururu.com.tw', N'台北市南港區研究院路88號', 1, NULL, N'上限中', 13)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (12, N'松山義式餐廳', NULL, N'王佳樺', N'李宗翰', N'店長', N'02-2766-1122', N'contact12@gururu.com.tw', N'台北市松山區八德路二段123號', 2, NULL, N'審核中', 14)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (13, N'桃園素食坊', NULL, N'張雅婷', N'黃建宏', N'副理', N'03-3322-4455', N'contact13@gururu.com.tw', N'桃園市桃園區中山路45號', 3, NULL, N'上限中', 15)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (14, N'苗栗炸物屋', NULL, N'陳冠宇', N'林欣怡', N'店長', N'037-123-4567', N'contact14@gururu.com.tw', N'苗栗縣苗栗市中正路78號', 4, NULL, N'結束合作', 16)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (15, N'彰化火鍋城', NULL, N'洪雅文', N'許宏偉', N'經理', N'04-7566-8899', N'contact15@gururu.com.tw', N'彰化縣彰化市民族路99號', 5, NULL, N'上限中', 17)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (16, N'雲林日式料理', NULL, N'蔡志強', N'吳佳怡', N'店長', N'05-6677-1122', N'contact16@gururu.com.tw', N'雲林縣斗六市中山路66號', 1, NULL, N'審核中', 18)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (17, N'嘉義港式餐廳', NULL, N'許冠宇', N'陳怡君', N'副理', N'05-2233-7788', N'contact17@gururu.com.tw', N'嘉義市東區仁愛路88號', 2, NULL, N'停用中', 19)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (18, N'台東烤肉屋', NULL, N'黃建宏', N'林雅婷', N'經理', N'089-123-3344', N'contact18@gururu.com.tw', N'台東縣台東市中華路45號', 3, NULL, N'上限中', 20)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (19, N'屏東咖啡館', NULL, N'張宏偉', N'李欣怡', N'店長', N'08-7654-2233', N'contact19@gururu.com.tw', N'屏東縣屏東市自由路88號', 4, NULL, N'審核中', 21)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (20, N'宜蘭披薩屋', NULL, N'林志明', N'王怡如', N'副理', N'03-8322-5566', N'contact20@gururu.com.tw', N'宜蘭縣宜蘭市中山路99號', 5, NULL, N'上限中', 22)
INSERT [dbo].[Vendors] ([VendorID], [VendorName], [TaxID], [OwnerName], [ContactName], [ContactTitle], [ContactTel], [ContactEmail], [Address], [VD_CategoryID], [LogoURL], [VD_Status], [AccountID]) VALUES (21, N'contact20@gururu.com.tw', NULL, N'待填寫', N'待填寫', N'待填寫', N'0000-0000', N'contact20@gururu.com.tw', N'待填寫', 1, NULL, N'審核中', 4081)
SET IDENTITY_INSERT [dbo].[Vendors] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_Accounts_Account]    Script Date: 2025/10/17 下午 01:20:19 ******/
ALTER TABLE [dbo].[Accounts] ADD  CONSTRAINT [UQ_Accounts_Account] UNIQUE NONCLUSTERED 
(
	[Account] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_Accounts_VerificationToken]    Script Date: 2025/10/17 下午 01:20:19 ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_Accounts_VerificationToken] ON [dbo].[Accounts]
(
	[VerificationToken] ASC
)
WHERE ([VerificationToken] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Members1__AB6E61646A337B0A]    Script Date: 2025/10/17 下午 01:20:19 ******/
ALTER TABLE [dbo].[Members1] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Members1__AB6E616482C0181B]    Script Date: 2025/10/17 下午 01:20:19 ******/
ALTER TABLE [dbo].[Members1] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Members1__AB6E6164F586D4C5]    Script Date: 2025/10/17 下午 01:20:19 ******/
ALTER TABLE [dbo].[Members1] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Members1__F3DBC57221D718C3]    Script Date: 2025/10/17 下午 01:20:19 ******/
ALTER TABLE [dbo].[Members1] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Members1__F3DBC57253BF1931]    Script Date: 2025/10/17 下午 01:20:19 ******/
ALTER TABLE [dbo].[Members1] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Members1__F3DBC572C10E6990]    Script Date: 2025/10/17 下午 01:20:19 ******/
ALTER TABLE [dbo].[Members1] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Accounts] ADD  CONSTRAINT [DF_Accounts_IsVerified]  DEFAULT ((0)) FOR [IsVerified]
GO
ALTER TABLE [dbo].[Members1] ADD  DEFAULT ('user') FOR [role]
GO
ALTER TABLE [dbo].[Members1] ADD  DEFAULT ((0)) FOR [is_verified]
GO
ALTER TABLE [dbo].[Members1] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Products] ADD  CONSTRAINT [DF_Products_EndDate]  DEFAULT ('2099-12-31') FOR [EndDate]
GO
ALTER TABLE [dbo].[Products] ADD  CONSTRAINT [DF_Products_Stock]  DEFAULT ((99999)) FOR [Stock]
GO
ALTER TABLE [dbo].[Members]  WITH CHECK ADD  CONSTRAINT [FK_Members_User] FOREIGN KEY([AccountID])
REFERENCES [dbo].[Accounts] ([AccountID])
GO
ALTER TABLE [dbo].[Members] CHECK CONSTRAINT [FK_Members_User]
GO
USE [master]
GO
ALTER DATABASE [gourmetmap] SET  READ_WRITE 
GO

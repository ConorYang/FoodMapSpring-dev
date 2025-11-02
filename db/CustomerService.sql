USE [gourmetmap]
GO

/****** Object:  Table [dbo].[CustomerService]    Script Date: 2025/10/20 下午 02:02:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CustomerService](
	[CustomerServiceID] [int] IDENTITY(1,1) NOT NULL,
	[Email] [nvarchar](30) NULL,
	[MemberID] [int] NULL,
	[VendorID] [int] NULL,
	[Subject] [nvarchar](50) NOT NULL,
	[Context] [nvarchar](300) NOT NULL,
	[CreateAt] [datetime] NOT NULL,
	[Reply] [nvarchar](300) NULL,
	[ReplyAt] [datetime] NULL,
	[AccountID] [int] NULL,
	[CS_Status] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_CustomerServiceID] PRIMARY KEY CLUSTERED 
(
	[CustomerServiceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


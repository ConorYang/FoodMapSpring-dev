USE [gourmetmap]
GO

/****** Object:  Table [dbo].[SearchHistory]    Script Date: 2025/10/26 下午 10:30:38 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SearchHistory](
	[SearchID] [int] IDENTITY(1,1) NOT NULL,
	[MemberID] [int] NULL,
	[Keyword] [nvarchar](100) NULL,
	[StyleID] [int] NULL,
	[City] [nvarchar](50) NULL,
	[PriceRange] [nvarchar](20) NULL,
	[TimeSlot] [nvarchar](20) NULL,
	[SearchTime] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[SearchID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SearchHistory] ADD  DEFAULT (getdate()) FOR [SearchTime]
GO

ALTER TABLE [dbo].[SearchHistory]  WITH CHECK ADD FOREIGN KEY([MemberID])
REFERENCES [dbo].[Members] ([MemberID])
GO

